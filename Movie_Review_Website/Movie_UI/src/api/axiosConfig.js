// import axios from 'axios';

// export default axios.create({
//     baseURL: 'https://9c96-103-106-239-104.ap.ngrok.io',
//     headers: {"ngrok-skip-browser-warning": "true"}
// });


import axios from 'axios';

const instance = axios.create({
  // configuration options
  baseURL: 'https://9c96-103-106-239-104.ap.ngrok.io',
  headers: {"ngrok-skip-browser-warning": "true"}
});

export default instance;

axiosConfig.js
