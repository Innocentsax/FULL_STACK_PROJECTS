import React, {useState, useEffect} from 'react';
import ReactPaginate from "react-paginate";
import axios from 'axios';
import { Card } from '@mui/material';
import { Link } from 'react-router-dom';
import './BlogPost.css'


 const BlogPost = () => {


    const [articles, setArticles] = useState([])
     
     const [page, setPage] = useState(1);

    const [isLastPage, setIsLastPage] = useState(false);

     const onNextPageHandler = ()=>{

         setPage(prevState => isLastPage? prevState : prevState + 1);


     }


     const onPrevPageHandler = ()=>{



         setPage(prevState => {
             let currentPage;
             if(prevState === 1) {
                 currentPage = 1;
             }
             else {
                 currentPage = prevState - 1;
             }
             return currentPage});


     }


    useEffect(() =>{
        // axios.get('https://jsonplaceholder.typicode.com/posts')
        axios.get(`https://fitnesso-app-new.herokuapp.com/articles/blogposts/${page}`)

            .then(res =>{
                console.log(res.data.content)
                console.table(res.data.content)
                if(res.data.content.length === 0){
                    setIsLastPage(true);
                }else{
                    setIsLastPage(false);
                }
                setArticles(res.data.content)
            })
            .catch(err =>{
                console.log(err)
            })
    }, [page])

     let content = <h2>No Articles To Display</h2>;





        //     if(articles.length > 0 ){
        //     content = articles.map((article) => {
        //     return(
        //     <Card key={article.id} >
        //     <img src={article.Image ? article.Image : "Image"}/>
        //     <div className="title">{article.title}</div>
        //     <div className="conten">{article.content}</div>
        //     <div className="authornam">{article.authorName}</div>
        // {/* <img key={article.id}> src={article.Image ? article.Image : "Image"} /> */}
        //     <Link to={`/blog/${article.id}`}>read more</Link>
        //     </Card>
        //     );
        // });
        // }







    return(

        <>
       <div className="card-wrapper">

           {
               articles?.map(article=>(
                       <Card key={article.id} className="blog-card">
                       <img src={article.Image ? article.Image : "Image"}/>
                       <div className="title"><h3>{article.title}</h3></div>
                       <div className="conten">{article.content}</div>
                       <div className="authornam">{article.authorName}</div>
                   {/* <img key={article.id}> src={article.Image ? article.Image : "Image"} /> */}
                       <Link to={`/blog/${article.id}`}>read more</Link>
                       </Card>
               ))
           }


           {/*{content}*/}

       </div>
            
            <div className="pagination-button">

                <button onClick={onPrevPageHandler}  >Prev</button>
                <button onClick={onNextPageHandler} disabled={isLastPage}>Next</button>

                
                
            </div>
    


        </>
    );
}

export default BlogPost