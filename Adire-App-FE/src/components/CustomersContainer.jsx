import React, { useEffect } from 'react';
// import Job from './Job';
import emptyImg from '../assets/images/emptyDataLogo.svg'
import Wrapper from '../assets/wrappers/Customers';
import { useSelector, useDispatch } from 'react-redux';
import Loading from './Loading';
import { getAllCustomers } from '../features/allCustomers/allCustomersSlice';
import LoadingBtn from './LoadingBtn';
import { Link } from 'react-router-dom';


// import PageBtnContainer from './PageBtnContainer';
//   return (
//     <Wrapper>
//       <h5>
//         {totalJobs} job{jobs.length > 1 && 's'} found
//       </h5>
//       <div className='jobs'>
//         {jobs.map((job) => {
//           return <Job key={job._id} {...job} />;
//         })}
//       </div>
//       {numOfPages > 1 && <PageBtnContainer />}
//     </Wrapper>
//   );


const CustomersContainer = ({ showCustomerList, setShowCustomerList }) => {

    const { customers, isLoading } = useSelector((store) => store.allCustomers);
    const dispatch = useDispatch();
    console.log(customers);

    // useEffect(() => {
    //     console.log("sogo");
    //     dispatch(getAllCustomers());
    // }, []);
  
    if (isLoading) {
      return (
        <Wrapper>
          <Loading />
        </Wrapper>
      );
    }

  if (customers.length === 0) {
    return (
      <Wrapper>
        <div>
          <img src={emptyImg} alt='empty logo' className='empty-img' />
          <p className='oh-no'>oh no!</p>
          <p>You have not created any customer's data yet</p>
          <button type='button' className='btn btn-outline' >
              create customer
          </button>
          {/* <LoadingBtn /> */}
        </div>
      </Wrapper>
    );
  }


  return (
      <div>
        <div>
          <h3>Customers</h3>
          <Link className='btn' >create customer</Link>
        </div>
      </div>
  )

}

export default CustomersContainer