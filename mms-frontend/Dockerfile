FROM node:14-alpine

WORKDIR /app
COPY server-build/package.json package.json
COPY server-build/package-lock.json package-lock.json

RUN npm install

COPY /server-build .

EXPOSE 3000
CMD ["node", "server.js"]
