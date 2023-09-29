// import './FeatureCard.css'


const FeatureCard = (props) => {
    return(
        <div className="flex flex-col items-center gap-2 md:flex-row md:justify-center" >
            <div className="text-[5rem] md:text-[6rem]">
                {props.icon}
            </div>
            <div className="flex flex-col gap-1 items-center md:items-start">
                <h6>{props.title}</h6>
                <p className="text-[#787878]">{props.details}</p>
            </div>
        </div>
    ) 
}

export default FeatureCard;