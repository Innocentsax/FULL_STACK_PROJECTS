import React from 'react';
import ReactDOM from 'react-dom/client';
import 'normalize.css';
import './index.css';
import App from './App';
import { store } from './store';
import { Provider } from 'react-redux';
import { GoogleOAuthProvider } from '@react-oauth/google';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <GoogleOAuthProvider clientId="178688632571-jmhkftmvgd028u2frl7ehs7jvqempa0v.apps.googleusercontent.com">
  {/* // <React.StrictMode> */}
    <Provider store={store}>
      <App />
    </Provider>
  {/* // </React.StrictMode> */}
  </GoogleOAuthProvider>
);

