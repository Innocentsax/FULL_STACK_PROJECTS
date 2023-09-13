import React from 'react'
import { BackArrow } from '../utils/SVGIcons'

const BackButton = ({ handleGoBack }) => {
  return (
    <button className='back-btn' onClick={handleGoBack}>
        <BackArrow />
        <p>Go back</p>
    </button>
  )
}

export default BackButton