import Slider from 'react-slick';
import "slick-carousel/slick/slick.css"
import "slick-carousel/slick/slick-theme.css"
import TestimonialCard from './TestimonialCard/TestimonialCard';
import { ArrowBackIos, ArrowForwardIos } from '@mui/icons-material';


export const PreviousBtn = (props) =>{
    const {className, onClick} = props;
    return(
        <div className={className} onClick={onClick}>
            <ArrowBackIos style={{color: 'gray', fontSize: '20px'}} />

        </div>
    )
}


export const NextBtn = (props) =>{
    const {className, onClick} = props;
    return(
        <div className={className} onClick={onClick}>
            <ArrowForwardIos style={{color: 'gray', fontSize: '20px'}} />

        </div>
    )
}


const TestimonialComponent = () => {

    return(
        <div className=''>
            <div className="mt-16">
                <h6 className='text-center overflow-hidden before:h-[1px] after:h-[1px] after:bg-black 
                    after:inline-block after:relative after:align-middle after:w-1/4 
                  before:bg-black before:inline-block before:relative before:align-middle 
                    before:w-1/4 before:right-2 after:left-2 text-xl p-4 mt-[4rem]'>TESTIMONIALS</h6>
            </div>
            <div className='flex justify-center w-[90%] mx-auto bg-[#f5f5f5] mt-5 '>
                <div className='w-[60%]'>
                        <Slider prevArrow={<PreviousBtn />} nextArrow={<NextBtn />} dots autoplay={1}>
                            <TestimonialCard avatar="../images/customer1.jpeg" text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla a mollis nibh. 
                                Quisque euismod maximus lacus et malesuada. Sed id finibus ante, ac ultrices sapien. 
                                Fusce fermentum ante sit amet neque dapibus imperdiet. Aenean pretium tincidunt mi ut tempus. 
                                Sed id lorem eget lacus egestas scelerisque. Mauris porta elit ut imperdiet pulvinar." cName="IKENNA AKUCHI" occupation="SOFTWARE DEVELOPER" />

                        <TestimonialCard avatar="../images/customer2.jpg" text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla a mollis nibh. 
                            Quisque euismod maximus lacus et malesuada. Sed id finibus ante, ac ultrices sapien. 
                            Fusce fermentum ante sit amet neque dapibus imperdiet. Aenean pretium tincidunt mi ut tempus. 
                            Sed id lorem eget lacus egestas scelerisque. Mauris porta elit ut imperdiet pulvinar." cName="MARGRETH MARTINS" occupation="SOFTWARE DEVELOPER" />

                        <TestimonialCard avatar="../images/customer3.jpeg" text="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla a mollis nibh. 
                            Quisque euismod maximus lacus et malesuada. Sed id finibus ante, ac ultrices sapien. 
                            Fusce fermentum ante sit amet neque dapibus imperdiet. Aenean pretium tincidunt mi ut tempus." cName="COURAGE OGHOGHO" occupation="SOFTWARE DEVELOPER" />
                        
                        </Slider>
                </div>
            </div>
                
        </div>
    );

}

export default TestimonialComponent;
