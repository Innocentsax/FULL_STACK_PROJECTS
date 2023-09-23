import React from 'react'
import "./product.css"
import ChevronRightIcon from '@mui/icons-material/ChevronRight';

export default function Product() {
  return (
      <>
          <div className="product_section">
        
        <h1 className="product_header">
            Premium workouts <br/>and fitness plan
        </h1>
        <p className="product_paragraph">
        Subscribe to our premium training services. Whatever your body goals may be, 
        <br/> get a personal trainer tailored to your needs.
        </p>
        <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href="/product/services" className="view_all"><ChevronRightIcon/> View all</a>
            
        </div>
        
    <div className="product_section2"> 
        
         <div className="product_container">
    
    
        <div className="products">
            <div className="product_photo">
            </div>

            <div className="product_title">
                <div className="product_title_wrapper">
                    <span className="colour_time">30 MINUTES </span>
                    <span className="colour_free"> FREE</span>
                </div>
            </div>
            
            <div className="product_name">
               <h5 className="name_product">
                   The Perfect Outdoor Workout
               </h5>
            </div>
            <div className="product_train">
            <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href="/v1"> <ChevronRightIcon/>Train now</a>
            </div>
        </div>


        <div className="products">
            <div className="product_photo1">
            </div>

            <div className="product_title">
                <div className="product_title_wrapper">
                    <span className="colour_time">1 HOUR </span>
                    <span className="colour_free"> FREE</span>
                </div>
            </div>
            
            <div className="product_name">
               <h5 className="name_product">
                   Back Day
               </h5>
            </div>
            <div className="product_train">
            <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href="/v2"> <ChevronRightIcon/> Train now</a>
            </div>
        </div>


        <div className="products">
            <div className="product_photo2">
            </div>

            <div className="product_title">
                <div className="product_title_wrapper">
                    <span className="colour_time">40 MINUTES </span>
                    <span className="colour_free"> FREE</span>
                </div>
            </div>
            
            <div className="product_name">
               <h5 className="name_product">
                   The Ultimate Abs Tabata
               </h5>
            </div>
            <div className="product_train">
            <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href="/v3"> <ChevronRightIcon/> Train now</a>
            </div>
            </div>
            </div>
            </div>

     <div className="product_section3">
        
        <h1 className="product_header2">
            Nutrional Products <br/>and health supplements
        </h1>
        <p className="product_paragraph2">
        Get all the vitamins and minerals needed to keep your energy levels 
        <br/>up. Stay sharp all day everday.
        </p>
        <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href="/product/products" className="view_all2"> <ChevronRightIcon/> VIEW ALL</a>
            
        </div>

        <div className="product_section4"> 
        
        <div className="product_container">
   
   
       <div className="products">
           <div className="product_photo">
           </div>

           <div className="product_title2">
               <div className="product_title_wrapper">
                   <span className="colour_time">3 Days A Week </span>
                   <span className="colour_free"> #10000</span>
               </div>
           </div>
           
           <div className="product_name">
              <h5 className="name_product">
                  Home Burn
              </h5>
           </div>
           <div className="product_train">
           <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href=""> <ChevronRightIcon/> View</a>
           </div>
       </div>
       <div className="products">
           <div className="product_photo1">
           </div>

           <div className="product_title">
               <div className="product_title_wrapper">
                   <span className="colour_time">30 Days </span>
                   <span className="colour_free"> #25000</span>
               </div>
           </div>
           
           <div className="product_name">
              <h5 className="name_product">
                  Shedded Meal Plan
              </h5>
           </div>
           <div className="product_train">
           <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href=""> <ChevronRightIcon/> View</a>
           </div>
       </div>


       <div className="products">
           <div className="product_photo2">
           </div>

           <div className="product_title">
               <div className="product_title_wrapper">
                   <span className="colour_time">7 Days A Week </span>
                   <span className="colour_free">#15000</span>
               </div>
           </div>
           
           <div className="product_name">
              <h5 className="name_product">
                  High Intensity Training
              </h5>
           </div>
           <div className="product_train">
           <a style={{display:'flex',alignItems:'center',fontWeight:'lighter'}} href=""> <ChevronRightIcon/> View</a>
           </div>
           </div>
       
           </div>
           </div>
    </>

  )
}
