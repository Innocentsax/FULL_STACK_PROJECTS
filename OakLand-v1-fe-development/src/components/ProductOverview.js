import { Empty } from "antd";
import { useEffect } from "react";
import { useAuth } from "../context/authcontext";
import Card from "./Card/Card";


const ProductOverview = () => {

    const { NewArrival, newArrival } = useAuth();

    useEffect(() => {
        NewArrival();
    }, [])

    return(
        <div className='max-w-[90%] mt-[-30px] p-4 bg-white mx-auto'>
            <div className='flex flex-wrap gap-[2rem] justify-center'>
                {  
                    newArrival.length > 0 ? 
                    newArrival.map(product =>
                        <Card title="NEW ARRIVAL" pName={product.name} image={product.imageUrl} id={product.id}  />)
                    :
                    < Empty />
                }
            </div>
        </div>
        
    );

}

export default ProductOverview;