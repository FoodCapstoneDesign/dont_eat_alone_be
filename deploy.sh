sudo docker login -u $DOCKER_USERNAME -p $DOCKER_TOKEN
IS_GREEN=$(sudo docker ps | grep app-container-green) # 현재 실행 중인 App이 green인지 확인합니다.

if [ -z "$IS_GREEN" ]; then # green 컨테이너가 실행 중이지 않다면
  echo "### Switching from BLUE to GREEN ###"

  echo "1. Pulling green image"
  sudo docker-compose pull app # green 이미지 내려받기

  echo "2. Starting green container"
  sudo docker-compose up -d app # green 컨테이너 실행

  echo "3. Health check for green..."
  while ! curl -s http://172.17.0.1:8080; do
    sleep 3
    echo "Waiting for green to be ready..."
  done

  echo "4. Switching Nginx to use green"
  sudo cp ./nginx/config/app.conf ./nginx/config/app.green.conf
  sudo docker exec mealmate-nginx nginx -s reload

  echo "5. Stopping blue container"
  sudo docker-compose stop app-blue
else
  echo "### Switching from GREEN to BLUE ###"

  echo "1. Pulling blue image"
  sudo docker-compose pull app-blue

  echo "2. Starting blue container"
  sudo docker-compose up -d app-blue

  echo "3. Health check for blue..."
  while ! curl -s http://172.17.0.1:8090; do
    sudo sleep 3
    echo "Waiting for blue to be ready..."
  done

  echo "4. Switching Nginx to use blue"
  sudo cp ./nginx/config/app.blue.conf ./nginx/config/app.conf
  sudo docker exec mealmate-nginx nginx -s reload

  echo "5. Stopping green container"
  sudo docker-compose stop app
fi
