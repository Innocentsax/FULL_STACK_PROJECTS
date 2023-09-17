import React,{useState,useEffect} from 'react'
// // import img1 from '../images/img1.jpeg'
// import img2 from '../images/img2.jpeg'
// import img3 from '../images/img3.jpeg'
// import img4 from '../images/img4.jpeg'
// import img5 from '../images/img5.jpeg'
// import img6 from '../images/img6.jpeg'
// import img7 from '../images/img7.jpeg'
import '../stylesheets/favouriteMain.css'
export default function FavouriteMain(props) {
    const [newFavourites,setNewFavourites] = useState(props.favourites);
    // useEffect(()=>{
    //     setNewFavourites(newFavourites);
    // },[newFavourites]);
    const remove = (id)=>{
        //alert(id);
        const updatedHero = newFavourites.filter(item => item.id !== id);
        setNewFavourites(updatedHero);
    }
    const checkout = ()=>{
    }
    return (
        <>
            <div className="right-container">
                {
                    newFavourites.map((product,key)=>{
                        return (
<div className="product" key={key}>
                    <div className="pro-container">
                        {/* <img src={img1} alt="" className="productImage"/> */}
                        <div className="productDetails">
                            <h6>{product.productName}</h6>
                            <small>{product.size}</small>
                            <small>{product.price}</small>
                        </div>
                    </div>
                    <div className="control">
                        <input type="button" value="BUY NOW" onClick={checkout}  />
                        <div className="bottom" onClick={()=>remove(product.id)}><i class="fas fa-times-circle"></i>REMOVE</div>
                    </div>
                </div>
                        )
                    })
                }
            </div>
        </>
    )
}