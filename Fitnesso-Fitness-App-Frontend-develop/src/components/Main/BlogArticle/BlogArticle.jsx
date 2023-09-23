import React from 'react'
import "./blogArticle.css"
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';



export default function blogArticle() {
  return (
      <>
    <div className="blogArticle_section1">
      <h2 className="blogArticle_header">
      Read our tips and tricks
      </h2>
      <p className="blogArticle_paragraph1">
      We post a lot of free content around health fitness and general wellbeing. Make sure to 
      <br/> check all of our posts down below.
      </p>
      <div className="view_button">
      <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href="#" className="view_all_post"> <ChevronRightIcon/>VIEW ALL POST</a>
      </div>
    </div>

    <div className="blogArticle_section2">
        <div className="blogArticle_container">
        <div className="blogArticle">
            <div className="blogArticle_photo">
                <div className="blogArticle_wrapper">
                    <div className="blogArticle_author">
                        <p style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} className="blogArticle_paragraph"><AccountCircleIcon/> Thomas Edison</p>
                    </div>
                    <div className="blogArticle_writeup">
                        <h5 className='writeup'>
                            The 10 best exercises to do in your park
                        </h5>
                    </div>
                    <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href="" className="read_more"> <ChevronRightIcon/> Read more</a>
                </div>
            </div>
        </div>

        <div className="blogArticle1">
            <div className="blogArticle_photo">
                <div className="blogArticle_wrapper">
                    <div className="blogArticle_author">
                        <p style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} className="blogArticle_paragraph"><AccountCircleIcon/> Thomas Edison</p>
                    </div>
                    <div className="blogArticle_writeup">
                        <h5 className='writeup'>
                            The 10 best exercises to do in your park
                            
                        </h5>
                    </div>
                    <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href=""> <ChevronRightIcon/> Read more</a>
                </div>
            </div>
        </div>

        <div className="blogArticle2">
            <div className="blogArticle_photo">
                <div className="blogArticle_wrapper">
                    <div className="blogArticle_author">
                        <p style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} className="blogArticle_paragraph"><AccountCircleIcon/> Thomas Edison</p>
                    </div>
                    <div className="blogArticle_writeup">
                        <h5 className='writeup'>
                            The 10 best exercises to do in your park
                        </h5>
                    </div>
                    <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href=""> <ChevronRightIcon/> Read more</a>
                </div>
            </div>
        </div>
        </div>
    </div>
    </>
  )
}
