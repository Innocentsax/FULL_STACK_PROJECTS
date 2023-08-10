// import Hero from "../hero/Hero"

// const Home = (movies) => {
//   return (
//     <Hero movies = {movies} />
 
//   )
// }

// export default Home

import React from 'react';
import Hero from '../hero/Hero';


const Home = (props) => {

  return (
    <>
        <h1>Home page</h1>
        <Hero movies={props.movies} />
    </>
  );
}

export default Home;

