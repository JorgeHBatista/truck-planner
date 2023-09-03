# Base image
FROM node:alpine as develop-stage

WORKDIR /usr/src/app
COPY package.json ./
RUN npm install\
        && npm install typescript -g

COPY . ./

# Build stage
FROM develop-stage as build-stage
RUN yarn build

# Production stage
FROM nginx:alpine as production-stage
COPY --from=build-stage /usr/src/app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]