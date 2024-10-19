FROM ubuntu:latest
LABEL authors="tox1n71"
FROM node
WORKDIR /simpel/frontend
COPY ../frontend/public /simpel/frontend/public
COPY ../frontend/package.json /simpel/frontend
COPY ../frontend/src /simpel/frontend/src
RUN npm install
CMD ["npm", "start"]