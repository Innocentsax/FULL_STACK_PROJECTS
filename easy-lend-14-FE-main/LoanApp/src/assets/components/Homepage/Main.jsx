import { MainDiv,
    MainContent,
    BoxALL,
    ALLBox,
    ALLBoxContent,
    BlueIvContainer,
    Number,
    LevelRol, 
    ImageTesti,
    BlueIv,
    ImgInRound,
    AboutUs,
    Content1,
    Testimonies,
    TestiBox,
    Button,
    Splash,
    TestimoniesOverLay,
    Award,
    Content2,
    MainTwo,
    AboutBox,
    BlueDiv,
    SplashContent,
    Certisfied,
    SplashImg,Con ,SplashBox} from "../Styled/Styled";
import frame from '../Images/frame.png'
import  { Link  }from 'react-router-dom'
import success from '../Images/Success.png'
import frame_ from '../Images/fi_award.png'
import article1 from '../Images/article1.png'
import article2 from '../Images/article2.png'
import article3 from '../Images/article3.png'
import ImageContent from "./ImageContent";

const Main =()=>{


    return (
        <>
        <MainDiv>
            <MainContent>
                <Content1>
                    <h1 style={{color: "#2D00F7",fontSize:"45px"}}>
                    Fulfil Your Financial Aspirations: Unleashing  Opportunities through  Lending Platforms
                    </h1>
                    <p>Experience Seamless Borrowing and Lending with Our Innovative Platform</p>
                    <Button>Get Started</Button>
                </Content1>
                <Content2>
                    <Splash>
                    <Con>
                    <SplashContent>
                            </SplashContent>
                            <SplashContent>
                            </SplashContent>
                            <SplashContent>
                            </SplashContent>
                            </Con>
                    </Splash>
                    <SplashImg>
                        <img src={frame} alt={frame} />
                    </SplashImg>
                    <Splash>
                    <SplashBox></SplashBox>
                    <Con>
                    <SplashContent>
                            </SplashContent>
                            <SplashContent>
                            </SplashContent>
                            <SplashContent>
                            </SplashContent>
                            </Con>
                    </Splash>

                </Content2>
            </MainContent>
     
        <MainTwo style={{background:"white"}}>
        <MainContent style={{height:"auto"}}>
            <Content1>
                <h1 style={{fontSize:"40px"}}>Our Loans will Fill Your Dreams Come True</h1>
                <LevelRol></LevelRol>
                <p>
                There are many variations of passages of lorem ipsum available the majority have suffered alteration in some form by injected humour. Duis aute irure dolor lipsum is simply free text available in the local markets in reprehenderit.Nam aliquam sem et tortor consequat mattis pellentesque semper tailored for specific uses and specific market segment.
                </p>
                <p><Link to="/">Read More</Link></p>
            </Content1>
            <Award>
            <Certisfied>
                <img src={frame_} alt=""  width={50}/>
                <h1>Award Winning</h1>
                <p>Finance categories winning more than 10 awards lorem ipsum available the majority have suffered alteration in some form by injected humour. Duis aute irure dolor lipsum is simply free text available in the local markets in reprehenderit.Nam aliquam</p>

            </Certisfied>
            <Certisfied>
                <img src={success} alt="" width={50} />
                <h1>Certified Company</h1>
                <p>Approved Finance company to provide loans Duis aute irure dolor lipsum is simply free text available in the local markets in reprehenderit.Nam aliquam</p>

            </Certisfied>
            </Award>
            
        </MainContent>
                
        </MainTwo>
        <MainTwo style={{background:"#FCFCFD"}}>
        <MainContent style={{flexDirection:"column"}}>
            <AboutUs>
                <h2>WHAT WE OFFER</h2>
                <h1>Our Loans Services</h1>
                <LevelRol></LevelRol>
            </AboutUs>
            <AboutBox>
            <ImageContent 
            type="Car" 
            image={article1}  
            text ="Car Loan provide low interest many variations of passages of lorem ipsum available the majority have some."/>
            <ImageContent 
            type="Wedding" 
            image={article2} 
            text ="For Couple provide easy and affordable with easy process lorem ipsum minim veniam aute irure lorm." />
            <ImageContent 
            type="Property" 
            image={article3} 
            text ="Everyone want to buy property so people want to buy home, land or commercial properly low interest." />
            </AboutBox>

        </MainContent>

        </MainTwo>

        <MainTwo className="mainTwo">
        <MainContent style={
            {height:"400px"
        }
        }>
            <Content1>
                <h1 className="contenth1">GET TWO KNOW ABOUT</h1>
                <h1 className="contenth2">
               
                Flexible and Quick Bussiness Loans For You</h1>
                <LevelRol></LevelRol>
            </Content1>
            <Content1
        >
                <p className="conte">Lorem ipsum dolor sit amet consectetur adipisicing elit. Non maxime repudiandae mollitia illum animi itaque exercitationem commodi nemo in dolor sunt, voluptates deserunt rem, maiores a! Excepturi eum earum amet.
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Est quia odio odit numquam veniam nisi provident sint placeat, officia at distinctio. Provident nisi eos accusamus unde aspernatur ipsum asperiores autem?
                </p>
            </Content1>

        </MainContent>
            </MainTwo>
            
         
            <MainContent className="mainContent">  
                   <BlueDiv>
                    <h2>WHY WE NEED TRUSTED</h2>
                    <h1>Most of the People Trust on Us For Fast Service</h1>
                    <LevelRol></LevelRol>
                    <p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Pariatur id quam excepturi repudiandae accusamus! Maxime aliquam amet eum dolore, error fugiat, tempora dignissimos nesciunt totam corporis iure quod necessitatibus dolores! lorem
                    aliquam amet eum dolore, error fugiat, tempora dignissimos nesciunt totam corporis iure quod necessitatibus dolores! lorem
                    </p>
                    <BoxALL>
                    <ALLBox>
                            <Number>
                                3
                            </Number>
                            <ALLBoxContent>
                                <p>Lorem ipsum dolor, sit amet 
                                consectetur adipisicing elitne
                                consectetur adipisicing elitne
                                </p>

                            </ALLBoxContent>
                            
                        </ALLBox>
                        <ALLBox>
                            <Number>
                                3
                            </Number>
                            <ALLBoxContent>
                                <p>Lorem ipsum dolor, sit amet 
                                consectetur adipisicing elitne
                                consectetur adipisicing elitne
                                </p>

                            </ALLBoxContent>
                            
                        </ALLBox>
                        <ALLBox>
                            <Number>
                                3
                            </Number>
                            <ALLBoxContent>
                                <p>Lorem ipsum dolor, sit amet 
                                consectetur adipisicing elitne
                                consectetur adipisicing elitne
                                </p>

                            </ALLBoxContent>
                            
                        </ALLBox>
                        <ALLBox>
                            <Number>
                                3
                            </Number>
                            <ALLBoxContent>
                                <p>Lorem ipsum dolor, sit amet 
                                consectetur adipisicing elitne
                                consectetur adipisicing elitne
                                </p>

                            </ALLBoxContent>
                            
                        </ALLBox>
                        </BoxALL>
                          

                   </BlueDiv>
            </MainContent>
            <MainContent style={{background:"unset"}}>
                <BlueDiv style={{width:"100%"}}>
                <h2 style={{color:"black"}}>WHAT PEOPLE ARE SAYING</h2>
                <h1 style={{color:"black",margin:"0px"}}>Testimonials</h1> 
              

             
                <Testimonies>
                <TestimoniesOverLay>
             
                   
                <TestiBox>
                        <ImageTesti>
                            <ImgInRound src={frame} alt="">
                            </ImgInRound>
                        </ImageTesti>
                        <p>Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. Arcu eget dolor varius vitae cursus quis. Imperdiet nunc enim in magna neque nisl. Lectus non dis a sit leo ipsum elementum ultrices velit.</p>
                        <p>

                        Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. 
                        </p>
                        <h5>opara ifenyi</h5>
                        <span>Data Analysis</span>

                    </TestiBox>
                   
                    <TestiBox>
                        <ImageTesti>
                            <ImgInRound src={article1} alt="">
                            </ImgInRound>
                        </ImageTesti>
                        <p>Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. Arcu eget dolor varius vitae cursus quis. Imperdiet nunc enim in magna neque nisl. Lectus non dis a sit leo ipsum elementum ultrices velit.</p>
                        <p>

                        Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. 
                        </p>
                        <h5>opara ifenyi</h5>
                        <span>Data Analysis</span>

                    </TestiBox>
                   
                    <TestiBox>
                        <ImageTesti>
                            <ImgInRound src={frame} alt="">
                            </ImgInRound>
                        </ImageTesti>
                        <p>Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. Arcu eget dolor varius vitae cursus quis. Imperdiet nunc enim in magna neque nisl. Lectus non dis a sit leo ipsum elementum ultrices velit.</p>
                        <p>

                        Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. 
                        </p>
                        <h5>opara ifenyi</h5>
                        <span>Data Analysis</span>

                    </TestiBox>
                   
                    <TestiBox>
                        <ImageTesti>
                            <ImgInRound src={article1} alt="">
                            </ImgInRound>
                        </ImageTesti>
                        <p>Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. Arcu eget dolor varius vitae cursus quis. Imperdiet nunc enim in magna neque nisl. Lectus non dis a sit leo ipsum elementum ultrices velit.</p>
                        <p>

                        Lorem ipsum dolor sit amet consectetur. Vestibulum purus mi risus faucibus ultricies morbi sagittis. 
                        </p>
                        <h5>opara ifenyi</h5>
                        <span>Data Analysis</span>

                    </TestiBox>
             
                       </TestimoniesOverLay>
                    </Testimonies>   
                               
                </BlueDiv>
            </MainContent>
            <BlueIv style={{
               background: "rgba(45, 0, 247, 1)"
            }}>
            <BlueIvContainer>
                <p>We Approve Loans</p>
                <h1>90%</h1>

</BlueIvContainer>
<BlueIvContainer>
<p>We Approve Loans</p>
                <h1>90%</h1>

</BlueIvContainer>
<BlueIvContainer>
<p>We Approve Loans</p>
                <h1>90%</h1>

</BlueIvContainer>
<BlueIvContainer>
<p>We Approve Loans</p>
                <h1>90%</h1>

</BlueIvContainer>
            </BlueIv>
            

            </MainDiv>
        </>
    )
}
export default Main;

