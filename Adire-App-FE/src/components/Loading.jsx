import Wrapper from "../assets/wrappers/Loading";

const Loading = ({ center }) => {
    // return <div className={center ? 'loading loading-center' : 'loading'}></div>;
    return (
        
        <Wrapper>
            <div className="dot-spinner">
                <div className="dot-spinner__dot"></div>
                <div className="dot-spinner__dot"></div>
                <div className="dot-spinner__dot"></div>
                <div className="dot-spinner__dot"></div>
                <div className="dot-spinner__dot"></div>
                <div className="dot-spinner__dot"></div>
                <div className="dot-spinner__dot"></div>
                <div className="dot-spinner__dot"></div>
            </div>
        </Wrapper>
    )
  };
  
  export default Loading;