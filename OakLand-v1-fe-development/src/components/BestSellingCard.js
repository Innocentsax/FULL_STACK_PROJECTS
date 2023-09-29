import { useAuth } from "../context/authcontext";

const BestSellingCard = ({pName, price, source, alt, id}) =>{

    const { AddToCartConfig } = useAuth();
    

    const addItemToCartHandler = () => {
      console.log(`"Product Item: ${id}`)
      AddToCartConfig(id);
    }


    return(
        <div className='mx-w-[800px] p-2 flex flex-col items-center'>
            <div className='bg-[#f5f5f5] mx-w-[400px]'>
                <img className="w-[200px] md:w-[250px] sm:w-[300px]" src={source} alt={alt} />
            </div>
            <div className='flex flex-col items-center'>
                <h5 className="mt-4 md:text-2xl sm:text-2xl text-1xl font-bold">{pName}</h5>
                <p className="mb-4 mt-3 text-1xl font-bold text-[brown]">₦{price}</p>
                <button className="" type='button' onClick={addItemToCartHandler}>ADD TO CART</button>
            </div>
        </div>
    );
}

export default BestSellingCard;