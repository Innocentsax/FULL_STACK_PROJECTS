import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { ProductProvider } from './context/productContext';
import { CategoryProvider } from './context/categoryContext';
import DataProvider from './context/authcontext';
import { PersonProvider } from './context/personContext';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // <React.StrictMode>
  <PersonProvider>
    <ProductProvider>
      <CategoryProvider>
        <DataProvider>
          <App />
        </DataProvider>
      </CategoryProvider>
    </ProductProvider>
  </PersonProvider>
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
