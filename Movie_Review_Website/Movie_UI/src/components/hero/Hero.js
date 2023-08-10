import React from 'react'
import './Hero.css'
import Carousel from 'react-material-ui-carousel';
import { Paper } from '@mui/material';


const Hero = ({movies}) => {
  return (
    <div className='movie-carousel-container'>
      <Carousel>
        {
            movies?.map((item) => {
                return(
                    <Paper>
                        <div className='movie-card-container'>
                            <div className="movie-card">
                                <div className="movie-detail">
                                    <div className="movie-poster">
                                        <img src={item.poster} alt=""/>
                                    </div>
                                    <div className="movie-title">
                                        <h4 key={movies.title}>{item.title}</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Paper>
                )
            })
        }
      </Carousel>
    </div>
  )
}

export default Hero
