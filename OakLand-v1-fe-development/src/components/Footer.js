import { BsTwitter, BsInstagram, BsClock, BsTelephone} from "react-icons/bs"
import { ImFacebook, ImLocation} from "react-icons/im"
import { Link } from "react-router-dom"
// BsTelephoneFill, BsFillClockFill,
// ImLocation,

export const FooterCard = (props) =>{
    return(
        <div className="">
            <h6 className=" font-bold mb-3 text-[#403414] text-[0.7rem] after:h-[4px] after:bg-black after:inline-block after:align-middle after:w-1/2" >{props.heading}</h6>
            <a href="/"><p>{props.link1}</p></a> 
            <a href="/"><p>{props.link2}</p></a> 
            <a href="/checkout"><p>{props.link3}</p></a> 
            <a href="/" ><p>{props.link4}</p></a> 
            <a href="/"><p>{props.link5}</p></a> 
        </div>
    )

}

const Footer = () => {
    return(
        <footer className="min-w-[100%] bg-[#bcbcbc] mt-[5rem] p-4 divide-y">
            <div className="flex gap-[5rem] md:flex-row flex-col md:justify-center">
                <div className="text-gray-600 flex flex-col md:items-start items-center gap-2">
                    <h6 className="font-bold text-black mb-3 after:h-[4px] text-[0.7rem] after:bg-black after:inline-block after:align-middle after:w-3/4">ABOUT US</h6>
                    
                    <div className='flex gap-2 items-center'>
                        <span className=''>< ImLocation /></span><span>Okhoromi community, Benin city, Edo state</span>
                    </div>
                    <div className='flex gap-2 items-center'>
                        <span className=''>< BsClock /></span><span>Sun - Sat: 9:00AM - 17:00PM</span>
                    </div>
                    <div className='flex gap-2 items-center'>
                        <span className=''>< BsTelephone /></span><span>+2348166386376</span>
                    </div>
                    <div className="flex gap-8 text-black">
                        <Link to='/'><ImFacebook /></Link>
                        <Link to='/'><BsTwitter /></Link>
                        <Link to='/'><BsInstagram /></Link>
                    </div>
                </div>
                <div className="flex gap-[3rem] md:gap-[5rem] mb-5 text-gray-600 justify-between">
                    <FooterCard heading="OTHER PAGES" link1="Home" link2="About Us" link3="Shop" link4="Blog" link5="Contact"/>
                    <FooterCard heading="QUICK LINKS" link1="Returns & refunds" link2="Order tracking" link3="Checkout" link4="Careers" link5="Cart"/>
                    <FooterCard heading="INFORMATION" link1="Privacy Policy" link2="Terms of service" link3="Disclaimer" link4="Credits" link5="FAQ"/>
                </div>
            </div>
            <div className="flex flex-col justify-between mt-2 py-2">
                <div className="flex gap-4">
                    <div>
                        <h6 className="text-[1.7rem] font-bold text-[#403414]">OAKLAND</h6>
                    </div>
                    <div className="flex gap-3 justify-center items-center">
                        <p className="text-[0.8rem]"><a href="/">PRIVACY</a></p>
                        <p className="text-[0.8rem]"><a href="/">HELP</a></p>
                        <p className="text-[0.8rem]"><a href="/">TERMS</a></p>
                    </div>
                </div>
                <div className="self-center mt-4">
                    <p>Copyright &#169; 2023. All Right Reserved.</p>
                </div>
            </div>
            
        </footer>
    )
}


export default Footer;