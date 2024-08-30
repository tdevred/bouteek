FROM node AS build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build --omit=dev

FROM nginx:latest

COPY --from=build /app/dist/ /var/www/html/

COPY ./confignginx /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]