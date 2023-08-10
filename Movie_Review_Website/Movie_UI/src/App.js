import './App.css';
import api from './api/axiosConfig';
import { useState, useEffect } from 'react';
import Layout from './components/Layout';
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import Home from './components/home/Home';
import { Switch } from '@mui/material';

function App() {
  const [movies, setMovies] = useState([]);

  const getMovies = async () => {
    try {
      const response = await api.get("http://localhost:8080/api/v1/movies");
      // console.log(response.data);
      setMovies(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getMovies();
  }, []);

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
            <Route path="/" element={<Layout/>}></Route>
            <Route path="/home" element={<Home movies={movies} />}></Route>
        </Routes>
      </BrowserRouter>
        
    </div>
  );
}

export default App;
