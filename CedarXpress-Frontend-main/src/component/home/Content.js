import React, { Fragment } from 'react'
import Card from './Card'
import BestSale from './BestSales'
import Feature from './Feature'


const Content = (props) => {
    const {HomeCss, trendProduct, featureProduct} = props 
    const showBadge = false;
    const productList = trendProduct.map(product => <Card  key={product.id} product={product} HomeCss={HomeCss}/>)

    return (
        <Fragment>
            <section className={`${HomeCss.minContainer}`}>
            <main>
                <div className={`${HomeCss.CardRow} ${HomeCss.CardOverlap}`}>
                    {productList}
                </div>
                <div className={HomeCss.BigTitle}>
                    <h3>Best Sales</h3>
                </div>
                <BestSale HomeCss={HomeCss} showBadge={showBadge}/>
                <Feature HomeCss={HomeCss} featureProduct={featureProduct}/>
                 <div className={HomeCss.BigTitle} style={{marginTop:'40px'}}>
                    <h3 style={{width:'400px'}}>Featured product</h3>
                </div>
                {/* <BestSale HomeCss={HomeCss} frequentSale={frequentSale} showBadge={showBadge} />
                <BestSale HomeCss={HomeCss} frequentSale={frequentSale} showBadge={!showBadge}/> */}
                <Feature HomeCss={HomeCss} featureProduct={featureProduct}/>
            </main>
        </section>

        </Fragment>
    )
}

export default Content