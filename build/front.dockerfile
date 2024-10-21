FROM ubuntu:latest
LABEL authors="tox1n71"
FROM node:18
WORKDIR /simpel/frontend
COPY public /simpel/frontend/public
COPY package.json /simpel/frontend
COPY src /simpel/frontend/src
RUN npm install
RUN npm install -g serve
RUN npm run build
CMD ["serve", "-s", "build"]