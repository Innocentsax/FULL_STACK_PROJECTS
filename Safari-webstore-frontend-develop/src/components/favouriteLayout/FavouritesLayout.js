import React, { useEffect, useState } from "react";
import { Grid, Container, Segment, GridColumn } from "semantic-ui-react";
import { NavLink } from "react-router-dom";
import "../../styles/Layout/_favourites_layout.scss";
import axios from "axios";
import BaseUrl from "../../apis/BaseUrl";
import productApis from "../../apis/ProductApi";
import { Link } from "react-router-dom";

function FavouritesLayout() {
  const [favourites, setFavourites] = useState([]);

  useEffect(async () => {
    const favouritesList = await productApis.getFavorites();

    console.log(favouritesList.data.body.content);

    setFavourites(favouritesList.data.body.content);

    
  }, []);
  console.log(favourites)
  return (
    <Container fluid padded className="favourites-container container">
      <Grid>
        <GridColumn width="16">
          <h4 className="myfavourites-title">My Favourites</h4>
          <Grid>
              {/* {favourites.map(favourite=>(
                  <p>{favourite.name}</p>
              ))} */}
            {favourites.map((favourite) => (
              <GridColumn width="8">
                <Segment>
                  <Grid>
                    <GridColumn width="4">
                      <img
                        src={favourite.productImages[0].image}
                        alt="favourite-img"
                        className="favourite-image"
                      ></img>
                    </GridColumn>
                    <GridColumn width="12">
                      <p className="favourite-title">{favourite.name}</p>
                      <p className="favourite-size">Size - EU: {favourite.sizes.map(size => (size.size)) + " "}</p>
                      <p className="favourite-amount"> ₦{favourite.price}</p>
                    </GridColumn>
                  </Grid>
                  <Grid>
                    <GridColumn width="10">
                      <Link to={"/product/" + favourite.id}><button className="favourite-buynow">BUY NOW</button></Link>
                    </GridColumn>
                    <GridColumn width="6">
                    </GridColumn>
                  </Grid>
                </Segment>
              </GridColumn>
            ))} 
          </Grid>
        </GridColumn>
      </Grid>
    </Container>
  );
}

export default FavouritesLayout;
