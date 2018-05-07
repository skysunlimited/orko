import styled from 'styled-components'
import { space, padding, fontSize, color, fontWeight, fontFamily } from 'styled-system'


const Tab = styled.button`
  border-radius: 2px;
  color: ${props => props.selected ? "black" : props.theme.colors.fore};
  background: ${props => props.selected ? props.theme.colors.white : "transparent"};
  border-color: ${props => props.selected ? props.theme.colors.white : "transparent"};
  border-width: 2px;
  border-style: solid;
  padding: 0 ${props => props.theme.space[1] + "px"} 0 ${props => props.theme.space[1] + "px"};
  font-size: ${props => props.theme.fontSizes[1] + "px"}
  margin: 0 0 0 ${props => props.theme.space[2] + "px"};
  &:hover {
    cursor: ${props => props.selected ? "auto" : "pointer"};
    background-color: ${props => props.theme.colors.deemphasis};
    border-color: ${props => props.theme.colors.deemphasis}};
  }
  ${color}
  ${padding}
  ${fontSize}
  ${fontFamily}
  ${fontWeight}
  ${space}
`;

export default Tab;