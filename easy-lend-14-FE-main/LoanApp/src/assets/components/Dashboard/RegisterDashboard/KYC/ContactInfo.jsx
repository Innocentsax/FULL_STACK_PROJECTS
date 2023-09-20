import active from '../images/active.png'
import inactive from '../images/inactive.png'
import {ContactStatus,StatusRow,StatusImage,StausText} from '../Styled-dashboard'

const ContactInfo =(prop)=>{
    return (
        <>
        <ContactStatus>
        <StatusRow>
                <StatusImage src={(prop.status)? active : inactive} alt={active}>
                </StatusImage>
                <StausText>Contact Information</StausText>
            </StatusRow>
            <StatusRow>
            <StatusImage src={(prop.status2)? active : inactive} alt={active}>
                </StatusImage>
                <StausText>Employement Status</StausText>
            </StatusRow>
            <StatusRow>
            <StatusImage src={(prop.status3)? active : inactive} alt={active}>
                </StatusImage>
                <StausText>Government Issue ID</StausText>
            </StatusRow>
            <StatusRow>
            <StatusImage src={(prop.status4)? active : inactive} alt={active}>
                </StatusImage>
                <StausText>Income Status</StausText>
            </StatusRow>
            <StatusRow>
            <StatusImage src={(prop.status5)? active : inactive} alt={active}>
                </StatusImage>
                <StausText>Proof of Address</StausText>
            </StatusRow>
            <StatusRow>
            <StatusImage src={(prop.status6)? active : inactive} alt={active}>
                </StatusImage>
                <StausText>Linked Bank Account</StausText>
            </StatusRow>

            
            
        </ContactStatus>
        </>
    )
}

export default ContactInfo;

