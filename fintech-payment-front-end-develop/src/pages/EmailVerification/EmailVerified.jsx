import React from 'react'
import styles from "./EmailVerified.module.css"
import { MdOutlineMarkEmailRead } from 'react-icons/md'

const EmailVerification = () => {
  return (
    <div className={styles.main_div}>
      <div className={styles.background}>
        <div className={styles.email_icon}>
          <MdOutlineMarkEmailRead fontSize="70px"></MdOutlineMarkEmailRead>
        </div>
        <div className={styles.h2}><h2>Verify your email</h2>
        </div>
        <div className={styles.paragraph}>
          <p>Hi there, use the link below to verify <br></br>
            your email and start enjoying Fintech</p></div>
        <div ><button className={styles.button} onClick={() => { }}>Verify email</button>
        </div>
      </div>

    </div>
  )
}

export default EmailVerification