import React from 'react'
import Navs from './Navs'
import SideNav from './SideNav'
import Hero from './Hero'
import Content from './Content'
import trends from '../data/trends.js'
import bestSales from '../data/bestSeles.js'
import HomeCss from './Home.module.css'
import table3 from '../../img/table3.PNG'
import desk3 from '../../img/desk3.PNG'
import axios from 'axios'

function Home(props) {
  const [trendProduct, setTrendProduct] = React.useState(trends);
    // const [frequentSale, setFrequentSale] = React.useState([]);
    const [featureProduct, setFeatureProduct] = React.useState([{
      id:'1',
      image: desk3,
      title:'HAPPY FRIDAY',
      name:'GET AN EXTRA 50% OFF'
    },
    {
      id:'2',
      image: table3,
      title:'FLASH SALE',
      name:'GET AN EXTRA 50% OFF'
    }]);
  //   const api =
  //   "http://localhost:8080/api/v1/products/?pageNo=0&pageSize=10&sortBy=id";

  // React.useEffect(() => {
  //   getProducts();
  // }, []);

  // const getProducts = async () => {
  //   try {
  //     const response = await axios.get(api);
  //     const responseData = response.data;
  //     setFrequentSale(responseData.slice(0,4));

  //     // console.log(responseData);
  //     // console.log("this is all the data", responseData);
  //   } catch (error) {
  //     console.log(error);
  //   }
  // };


  return (
    <div className={`${HomeCss.wrapper}`}>
      <Hero HomeCss={HomeCss}/> 
      <Content 
      trendProduct={trendProduct} 
      // frequentSale={frequentSale}
      featureProduct={featureProduct}
      HomeCss={HomeCss}
      />
      
      {props.sideNav && <SideNav
       HomeCss={HomeCss}
       showSideNav={props.showSideNav} 
      bestSales={bestSales}/>}
    </div>
  );
}

export default Home;