const BtnContainer = ({ isProfilePage, setIsProfilePage }) => {
    return (
      <div className='btn-container'>
        <button
            onClick={() => setIsProfilePage(true)}
            className={isProfilePage ? 'settings-btn active-btn' : 'settings-btn'}
        >
            edit profile
        </button>
        <button
            onClick={() => setIsProfilePage(false)}
            className={!isProfilePage ? 'settings-btn active-btn' : 'settings-btn'}
        >
            change password
        </button>
        {/* {jobs.map((item, index) => {
          return (
            <button
              key={item.id}
              onClick={() => setCurrentItem(index)}
              className={index === currentItem ? 'job-btn active-btn' : 'job-btn'}
            >
              {item.company}
            </button>
          );
        })} */}
      </div>
    );
  };
  export default BtnContainer;