import ReactPaginate from 'react-paginate';
import useProduct from '../../hooks/useProduct';
import ProductItem from '../../components/ProductCard/ProductItem'
import { Spin } from 'antd';

import {
  LoadingOutlined, CaretLeftOutlined, CaretRightOutlined
} from '@ant-design/icons';

const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

const Pagination = ({ url, isEditable }) => {
    const { totalPages, setPageNumber, products, fetching } = useProduct()
    const changePage = ({ selected }) => setPageNumber(selected)
    

  return (
    <div>
      { fetching ?
          <div style={{ width: "100%", display: "flex", height:"50%", alignItems: "center", justifyContent: "center", }}>
              <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
        </div> :
      ( products?.length > 0 &&
          <div className="this-product-container">
            <div className="favorites-div">
              { 
                  products.map((product, index) => 
                          <ProductItem key={index} product={ product } url={url} isEditable={isEditable} /> 
              )}
            </div>

            </div>
        ) 
    }


            <ReactPaginate 
              previousLabel={<CaretLeftOutlined />}
              nextLabel={<CaretRightOutlined />}
              breakLabel="..."
              pageCount={totalPages} 
              onPageChange={changePage}
              containerClassName={"paginationBtns"}
              previousLinkClassName={"prevBtn"}
              nextLinkClassName={"nextBtn"}
              disabledClassName={"paginationDisabled"}
              activeClassName={"paginationActive"}
            />

  { products?.length === 0 && !fetching &&
    <div className="favorites-div" style={{minHeight: "40vh"}}>
      "No data"
    </div>
  }
  </div>
  )
}

export default Pagination