import React from 'react'

const Card = ({ children }) => {
  return (
    <article className="card">
      <div className="card-header">
        {children}
      </div>
    </article>
  )
}

export default Card