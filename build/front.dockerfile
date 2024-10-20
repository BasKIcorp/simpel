FROM ubuntu:latest
LABEL authors="tox1n71"
FROM node:18
WORKDIR /simpel/frontend
COPY public /simpel/frontend/public
COPY package.json /simpel/frontend
COPY src /simpel/frontend/src
RUN npm install
CMD ["npm", "start"]