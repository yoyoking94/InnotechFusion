# Étape 1 : compilation du projet Angular
FROM node:20-alpine AS etape-compilation
WORKDIR /app
COPY package*.json .
RUN npm install
COPY . .
RUN npm run build

# Étape 2 : servir l'application avec Nginx
FROM nginx:alpine
COPY --from=etape-compilation /app/dist/frontend/browser /usr/share/nginx/html
EXPOSE 80
