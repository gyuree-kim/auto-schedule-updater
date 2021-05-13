# Auto-schedule-updater
Based on korean nlp, this mobile app will obtain texts related to scheduling and then update it.

## How to start
In server directory, start with the command
```
node app.js
```
or
```
pm2 start node app.js
```
and you can terminate the server with
```
^C
```
or
```
pm2 stop app.js
```
### Install pm2
Make sure you should install pm2 to use runtime nodejs process manager. If you havent't, install it.
```
npm install pm2 -g
```

## How to use
- Only android users are available
- Download link: [playstore]()

## Tech stack
- `frontend`: `android studio`
- `backend`: `nodejs`, `express`, `mongodb`, `mongoose`
- `deployment`: `AWS`

## How it works
### NLP package
- Python package based on korean Natural Language Processing(NLP)

### Frontend
- `android studio`

### Backend
- Installed packages:
    `body-parser`, `cors`, `cryto`, `express`, `mongodb`, `mongoose`, `morgan`