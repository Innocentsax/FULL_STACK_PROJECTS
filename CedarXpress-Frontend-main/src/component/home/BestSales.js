import React from "react";
import RateCard from './RateCard';
import axios from "axios";

const BestSale = (props) => {
    const {HomeCss, showBadge} = props;
    const [frequentSale, setFrequentSale] = React.useState([]);
    

    const api =
    "http://localhost:8080/api/v1/products/?pageNo=0&pageSize=10&sortBy=id";

  React.useEffect(() => {
    getProducts();
  }, []);

  const getProducts = async () => {
    try {
      const response = await axios.get(api);
      const responseData = response.data;
      setFrequentSale(responseData.slice(0,4));

      // console.log(responseData);
      // console.log("this is all the data", responseData);
    } catch (error) {
      console.log(error);
    }
  };


    const frequentSaleList = frequentSale.map(frequent => <RateCard 
        key={frequent.id} 
        frequent={frequent}
        HomeCss={HomeCss}
        showBadge={showBadge}
        />)
    return (
        <div className={`${HomeCss.CardRow} ${HomeCss.rateCard}`}> 
            {frequentSaleList}
        </div>
    )
}

export default BestSale