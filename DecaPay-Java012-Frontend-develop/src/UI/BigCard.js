import classes from './BigCard.module.css'
const BigCard = (props) => {
    return ( 
        <div className={`${classes.bigcard} ${props.className}`}>
            {props.children}
        </div>
     );
}
 
export default BigCard;