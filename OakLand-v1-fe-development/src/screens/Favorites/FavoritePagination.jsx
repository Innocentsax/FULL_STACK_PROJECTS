
import ReactPaginate from 'react-paginate';
import useProduct from '../../hooks/useProduct';
import ProductItem from '../../components/ProductCard/ProductItem'

import { Spin } from 'antd';

import {
  LoadingOutlined, 
  CaretLeftOutlined, 
  CaretRightOutlined
} from '@ant-design/icons';

const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

const FavoritePagination = ({ url, isEditable }) => {
    const { favTotalPages, setFavPageNumber, favorites, fetching } = useProduct()
    const changePage = ({ selected }) => setFavPageNumber(selected)

  return (
    <div>

        { fetching ?
            <div style={{ width: "100%", display: "flex", height:"50%", alignItems: "center", justifyContent: "center", }}>
            <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
            </div> :
        (favorites?.length > 0 &&
            <div className="this-product-container">
            <div className="favorites-div">
                { 
                    favorites.map((product, index) => 
                            <ProductItem key={index} product={ product } url={url} isEditable={isEditable} /> 
                )}
            </div>

            <ReactPaginate 
                previousLabel={<CaretLeftOutlined />}
                nextLabel={<CaretRightOutlined />}
                pageCount={favTotalPages} 
                onPageChange={changePage}
                containerClassName={"paginationBtns"}
                previousLinkClassName={"prevBtn"}
                nextLinkClassName={"nextBtn"}
                disabledClassName={"paginationDisabled"}
                activeClassName={"paginationActive"}
            />
            </div>
      )
    }

  { favorites?.length === 0 && !fetching &&
    <div className="favorites-div" style={{minHeight: "40vh"}}>
      "You don't have any favorites."
    </div>
  }
  </div>
  )
}

export default FavoritePagination