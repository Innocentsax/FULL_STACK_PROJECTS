import { Link } from "react-router-dom";

const Card = ({title, pName, image, id}) =>{
    return(
        <div className=" bg-[#f5f5f5] w-[390px] h-[165px]" >
            <div className='flex flex-col-2'>
                <div className='flex flex-col basis-1/2 p-4'>
                    <h5 className="text-[1rem] text-[#d8a600]">{title}</h5>
                    <h3 className="text-[1.2rem] font-bold  mt-2">{pName}</h3>
                    <Link className="text-[0.9rem] mt-3" to={`/shop/products/${id}`}>SHOP NOW &#8594;</Link>
                </div>
                <div className='w-[100px] basis-1/2'>
                    <img className="w-[150px] h-[150px] sm:w-[200px] " src={image} alt="#" />
                </div>
            </div>
        </div>
        
    );
    

}

export default Card;