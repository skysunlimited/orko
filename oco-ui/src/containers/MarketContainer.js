import React from "react"
import { connect } from "react-redux"

import Section from "../components/primitives/Section"
import Tab from "../components/primitives/Tab"
import OrderBook from "../components/OrderBook"
import styled from "styled-components"
import Loading from "../components/primitives/Loading"
import { getTopOfOrderBook } from "../selectors/coins"

const Split = styled.section`
  display: flex;
  flex-direction: row;
  width: 100%;
`

const BidSide = styled.div`
  flex-grow: 1;
  border-left: 1px solid rgba(0,0,0,0.2);
`

const AskSide = styled.div`
  flex-grow: 1;
`

const loading = <Loading p={2} />

const buttons = (
  <span>
    <Tab selected>Order Book</Tab>
    <Tab>History</Tab>
  </span>
)

const MarketContainer = ({ orderBook }) => {
  const content = orderBook ? (
    <Split>
      <AskSide><OrderBook orders={orderBook.bids} direction="BID" /></AskSide>
      <BidSide><OrderBook orders={orderBook.asks} direction="ASK" /></BidSide>
    </Split>
  ) : (
    loading
  )
  return (
    <Section
      nopadding
      id="marketData"
      heading="Market"
      buttons={() => buttons}
    >
      {content}
    </Section>
  )
}

function mapStateToProps(state) {
  return {
    orderBook: getTopOfOrderBook(state)
  }
}

export default connect(mapStateToProps)(MarketContainer)
