#!/bin/bash

# 현재 실행 중인 Green 컨테이너 확인
IS_GREEN=$(sudo docker ps | grep -w app-container)

if [ -n "$IS_GREEN" ]; then
  echo "### Switching from GREEN to BLUE ###"

  echo "1. Pulling blue image"
  sudo docker-compose pull app-container-b

  echo "2. Starting blue container"
  sudo docker-compose up -d app-container-b

  echo "3. Health check for blue..."
  for i in {1..30}; do
    if curl -s http://172.17.0.1:8090 > /dev/null; then
      echo "Blue is ready!"
      break
    fi
    if [ $i -eq 30 ]; then
      echo "Blue failed to start. Aborting..."
      exit 1
    fi
    sleep 3
    echo "Waiting for blue to be ready... (Attempt $i/30)"
  done

  echo "4. Switching Nginx to use blue"
  sudo docker exec mealmate-nginx sh -c "sed -i 's|proxy_pass http://backend_green;|proxy_pass http://backend_blue;|' /etc/nginx/conf.d/app.conf && nginx -s reload"

  echo "5. Stopping green container"
  sudo docker-compose stop app-container

else
  echo "### Switching from BLUE to GREEN ###"

  echo "1. Pulling green image"
  sudo docker-compose pull app-container

  echo "2. Starting green container"
  sudo docker-compose up -d app-container

  echo "3. Health check for green..."
  for i in {1..30}; do
    if curl -s http://172.17.0.1:8080 > /dev/null; then
      echo "Green is ready!"
      break
    fi
    if [ $i -eq 30 ]; then
      echo "Green failed to start. Aborting..."
      exit 1
    fi
    sleep 3
    echo "Waiting for green to be ready... (Attempt $i/30)"
  done

  echo "4. Switching Nginx to use green"
  sudo docker exec mealmate-nginx sh -c "sed -i 's|proxy_pass http://backend_blue;|proxy_pass http://backend_green;|' /etc/nginx/conf.d/app.conf && nginx -s reload"

  echo "5. Stopping blue container"
  sudo docker-compose stop app-container-b
fi

echo "Deployment completed successfully!"
