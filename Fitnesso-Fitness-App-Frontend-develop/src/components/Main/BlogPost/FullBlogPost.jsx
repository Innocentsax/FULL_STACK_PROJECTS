import React, {useState, useEffect} from 'react';
import ReactPaginate from "react-paginate";
import axios from 'axios';
import { useParams, Link } from 'react-router-dom'
import { Card } from '@mui/material';
import './FullBlogPost.css'

const FullBlogPost = () => {
    const { id } = useParams()
    const [articles, setArticles] = useState(null)
    const [isLastPage, setIsLastPage] = useState(false);
    useEffect(() =>{
        axios.get(`https://fitnesso-app-new.herokuapp.com/articles/blogposts/single_article/${id}`)

            .then(res =>{
                console.log("Inside get a product", res)
                console.table(res.data)
                console.table(res.data)
                setArticles(res.data)
            })
            .catch(err =>{
                console.log( "eerror" + err)
            })
    }, [id,articles])

    let content = <h2>single</h2>;



    return(

            <div className="single-blog-display" >
                { articles ?
                        <Card key={articles.id}  className="single-blog-card" > 
                            <img src={articles.image}/>
                            <div>{articles.title}</div>
                            <div>{articles.content}</div>
                            <div>{articles.authorName}</div>
                        </Card> : <h1> </h1>
                }
            </div>

    )
}




export default FullBlogPost