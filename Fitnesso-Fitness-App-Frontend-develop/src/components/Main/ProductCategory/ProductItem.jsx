import React from 'react';
import Colors from '../../services/Colors';
import DetailsThumb from '../../services/DetailsThumb';
import './ViewProduct.css';

class ProductItem extends React.Component{

  myRef = React.createRef();


  render(){
    const {products, index} = this.state;
    return(
      <div className="app">
        {
          products.map(item =>(
            <div className="details" key={item._id}>
              <div className="big-img">
                <img src={item.src[index]} alt=""/>
              </div>

              <div className="box">
                <div className="row">
                  <h2>{item.title}</h2>
                  <span>${item.price}</span>
                </div>
                <Colors colors={item.colors} />

                <p>{item.description}</p>
                <p>{item.content}</p>

                {/* <DetailsThumb images={item.src} tab={this.handleTab} myRef={this.myRef} /> */}
                <button className="cart">Add to cart</button>

              </div>
            </div>
          ))
        }
      </div>
    );
  };
}

export default ProductItem;