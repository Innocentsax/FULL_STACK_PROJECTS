import React from 'react';


const Feature = (props) => {
    const {HomeCss, featureProduct} = props;
    return (
        <div className={`${HomeCss.CardRow} ${HomeCss.featureCard}`}> 
            <div className={HomeCss.leftSide}>
                <img src={featureProduct[0].image} alt={featureProduct[0].id}/>
                <div className={HomeCss.featureText}>
                    <h1 className={HomeCss.fontSm}>{featureProduct[0].title}</h1>
                    <p style={{fontSize:'2rem'}}>
                        {featureProduct[0].name.substring(0, 12)}
                        <br/>
                    {featureProduct[0].name.substring(12)
                    }</p>
                </div>
            </div>
            <div className={HomeCss.rightSide}>
                <img src={featureProduct[1].image} alt={featureProduct[1].id}/>
                 <div className={HomeCss.featureText}>
                    <h1 className={HomeCss.fontSm}>{featureProduct[1].title}</h1>
                    <p style={{fontSize:'2rem'}}>
                        {featureProduct[1].name.substring(0, 10)}
                        <br/>
                    {featureProduct[1].name.substring(10)
                    }</p>
                </div>
                </div>
        </div>
    )
}

export default Feature