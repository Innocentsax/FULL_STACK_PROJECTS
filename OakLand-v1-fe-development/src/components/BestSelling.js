
import { Empty } from 'antd';
import { useEffect } from 'react';
import { useAuth } from '../context/authcontext';
import BestSellingCard from './BestSellingCard';


const BestSelling = () =>{


    const { BestSelling, bestSelling } = useAuth();

    useEffect(() =>{
        BestSelling();
    }, [])

    return(
        <div className="">
            <h6 className='text-center overflow-hidden before:h-[1px] after:h-[1px] after:bg-[#959494]
           after:inline-block after:relative after:align-middle after:w-1/4 
           before:bg-[#959494] before:inline-block before:relative before:align-middle 
           before:w-1/4 before:right-2 after:left-2 text-xl p-4 mt-[4rem]'> BEST SELLING </h6>
            <div className="mt-8 flex justify-center flex-wrap gap-5">
                {
                    bestSelling.length > 0?
                    bestSelling.map(product =>
                            <BestSellingCard pName={product.name} price={product.price} source={product.imageUrl} alt="#" id={product.id} />
                        )
                    : 
                    <Empty />
                }
            
            </div> 
        </div>

    );
}

export default BestSelling;


