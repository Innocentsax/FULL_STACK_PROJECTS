import React from "react";

const Card = (props) =>{
const {HomeCss} = props;
const {name, title, url} = props.product;
return(
<article className={`${HomeCss.advertDiv}`}>
    <div className={`${HomeCss.carddescription}`}>
        <h4 className={HomeCss.h4}>{title}</h4>
        <p className={HomeCss.name}>{name}</p>
        <p className={HomeCss.showMore}>show now --</p>
    </div>
    <div className={HomeCss.imgContainer}>
        <img src={url} alt={name} />
    </div>
</article>
)
}

export default Card