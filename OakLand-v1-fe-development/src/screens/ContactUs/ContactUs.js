import React from 'react';
import { HiOutlineClock } from 'react-icons/hi';
import Input from '../../components/Input/Input';
import './contact.css';

const ContactUs = () => {
  return (
   <div className='container contact'>
      <div className='row'>
        <div className='col-md-6'>
           <p className='text-3xl text-bold-900 text-black-900'>SUBMIT A REQUEST</p>
           <p className='mt-7 mb-9 text-xl font-thin text-gray-400'>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>

           <section className='d-flex justify-content-between'>
              <div className=''>
                <div className='d-flex'>
                  <HiOutlineClock  className='text-gray-300 font-thin text-5xl'/>
                  <h2 className='ms-3 text-2xl text-bold-900 text-black-900'>OPEN HOURS</h2>
                </div>
                
                <p className='ms-4 ps-2 mb-9 text-xl font-thin text-gray-400'>Mon - Fri: 8:30 - 18:00</p>
              </div>

              <div className=''>
                <div className='d-flex'>
                  <HiOutlineClock />
                  <h2 className='ms-3'>OPEN HOURS</h2>
                </div>
                
                <p className='ms-4 ps-2'>Mon - Fri: 8:30 - 18:00</p>
              </div>
           </section>

           <section className='d-flex justify-content-between'>
              <div className=''>
                <div className='d-flex'>
                  <HiOutlineClock />
                  <h2 className='ms-3'>OPEN HOURS</h2>
                </div>
                
                <p className='ms-4 ps-2'>Mon - Fri: 8:30 - 18:00</p>
              </div>

              <div className=''>
                <div className='d-flex'>
                  <HiOutlineClock />
                  <h2 className='ms-3'>OPEN HOURS</h2>
                </div>
                
                <p className='ms-4 ps-2'>Mon - Fri: 8:30 - 18:00</p>
              </div>
           </section>

        </div>

        <div className='col-md-6 ps-6'>
          <form>
            <div className='row'>
              <div className='col-md-6'>
                <Input type='text' placeholder='Name' id='name' label='Name'/>
              </div>
              <div className='col-md-6'>
                <Input type='number' placeholder='Phone' id='phone' label='Phone'/>
              </div>
            </div>

                <Input type='email' placeholder='Email' id='email' label='Email'/>
                <div className='col-md-12'>
                  <textarea placeholder='Message' className='w-100 border-x-0 border-t-0 shadow-none outline-none border-b-1 border-gray-200 rounded-none font-thin text-lg pt-5 pb-8 mb-4'></textarea>
                </div>
                <button className='w-100 bg-dark text-white text-bold-900 md:text-lg sm:text-sm py-3 text-md' >SEND MESSAGE</button>
          </form>
        </div>
      </div>
   </div>
  )
}

export default ContactUs;