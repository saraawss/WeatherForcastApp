'use strict';



const React = require('react');
const ReactDOM = require('react-dom')
const when = require('when');
const axios = require('./axios.min.js');



const root = '/api';


class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {forcasts: [],forcastsD: [], latitude: null, longitude: null,  error: null,cityname: null ,countryname: null};
		this.getDetailForcast = this.getDetailForcast.bind(this);
	}

	loadFromServer(latitude, longitude) {
		
		axios.get("./getForcast?lat="+latitude+"&lon="+longitude)
	      .then(res => {
	        const forcasts = res.data;
	        
	        this.setState({ forcasts: forcasts.list,
	        				cityname: forcasts.city.name,
	        				countryname: forcasts.city.country});
	      });
	}
	
	getLocation() {
		navigator.geolocation.getCurrentPosition(
			(position) => {
			 this.loadFromServer(position.coords.latitude, position.coords.longitude);
			 this.setState({
			 	latitude: position.coords.latitude,
			    longitude: position.coords.longitude,
			    error: null,
			});
		},
		(error) => this.setState({ error: error.message }),
			{ enableHighAccuracy: true, timeout: 20000, maximumAge: 1000 },
		);
		
	}
	
	getDetailForcast(date) {

		axios.get("/getForcastDetail/"+date)
		  .then(res => {
	        const forcasts = res.data;
	        
	        this.setState({ forcastsD: forcasts.list});
	      });
	}
	
	// tag::follow-1[]
	componentDidMount() {
		this.getLocation();
		
	}
	// end::follow-1[]

	render() {
		return (
		
		<div className="container">
		  <div className="nauk-info-connections text-center">
		  	<h2 className="mt-5 mb-3">Weather Forcast</h2>
		  </div>
		  
		  <div className="card border-secondary">
		      <div className="card-header">Weather Summary in {this.state.cityname}, {this.state.countryname} </div>
		      <div className="card-body text-secondary">
		      	 <ForcastList forcasts={this.state.forcasts}
		      	 			  forcastsD={this.state.forcastsD}
						      getDetailForcast={this.getDetailForcast}/>
			  </div>
		    </div>
		    
		    
		    
		</div>
		
			
		)
	}
}

class ForcastList extends React.Component {

	constructor(props) {
		super(props);
		
	}

	
	// tag::forcats-list-render[]
	render() {
		var forcastList = this.props.forcasts.map(forcast =>
						<Forcast 
						forcast={forcast}
						forcasts={this.props.forcasts}
						forcastsD={this.props.forcastsD}
						getDetailForcast={this.props.getDetailForcast}/>
		);
		

		return (
			<div className="row justify-content-xs-center">
				{forcastList}
			</div>
			
			
		)
	}
	// end::forcasts-list-render[]
}

//tag::forcast[]
class Forcast extends React.Component {

	constructor(props) {
		super(props);
	}
	
	getDay(date){
		var date_ = new Date(date);
		var day = date_.getDay();
		
		switch(day) {
	    case 0:
	        day = "Sunday";
	        break;
	    case 1:
	        day = "Monday";
	        break;
	    case 2:
	        day = "Tuesday";
	        break;
	    case 3:
	        day = "Wednesday";
	        break;
	    case 4:
	        day = "Thursday";
	        break;
	    case 5:
	        day = "Friday";
	        break;
	    case 6:
	        day = "Saturday";
	        break;
	    default:
	        break;
		}
		
		return day;
	}

	render() {
		
		var weatherList = this.props.forcast.weather.map(weather =>
			<Weather 
			weather={weather}
			weathers={this.props.forcast.weather}/>
		);
		
		
		return (
			<div className="col-sm col-12 mb-2 data-column">
				<div className="col column-heading">
			          {this.getDay(this.props.forcast.dt_txt)}
			    </div>
			        
				{weatherList}
				<div className="col">
	          		{this.props.forcast.main.temp}
	        	</div> 
	        	<Detail forcast={this.props.forcast}
	        			forcastsD={this.props.forcastsD}
	        			getDetailForcast={this.props.getDetailForcast}/>
			</div>
		)
	}
}
// end::forcast[]


//tag::weather[]
class Weather extends React.Component {

	constructor(props) {
		super(props);
	}

	getIcon(iconImage){
		
		switch(iconImage) {
	    case "Clear":
	    	iconImage = "fab fa-skyatlas fa-3x icon-image";
	        break;
	    case "Clouds":
	    	iconImage = "fas fa-cloud fa-3x icon-image";
	        break;
	    case "Rain":
	    	iconImage = "fas fa-tint fa-3x icon-image";
	        break;
	    case "Sunny":
	    	iconImage = "fas fa-sun fa-3x icon-image";
	        break;
	    
	    default:
	        break;
		}
		
		return iconImage;
	}
	
	render() {
		
		
		
		return (
				<span>
				<div className="col">
          			<i className={this.getIcon(this.props.weather.main)}></i>
        		</div>
        		<div className="col">
          			{this.props.weather.main}
        		</div> 
				</span>
		)
	}
}
// end::weather[]

//tag::detail[]
class Detail extends React.Component {

	constructor(props) {
		super(props);
		this.handleDetail = this.handleDetail.bind(this);
	}

	handleDetail(e) {
		e.preventDefault();
	
		this.props.getDetailForcast(this.props.forcast.dt_txt);
	}
	
	render() {
		
		var forCastDetailList = this.props.forcastsD.map(forcastD =>
						<ForcastD 
						forcastD={forcastD}
						forcastsD={this.props.forcastsD}/>
		);
		
		
		var dialogId = "DetailForcast-" + this.props.forcast.dt_txt;
		
		return (
				
				<div className="col" key={this.props.forcast.dt_txt}>
          			<button type="button" onClick={this.handleDetail} className="btn btn-outline-info" data-toggle="modal" data-target={"#"+dialogId}>
					More
					</button>
					
					<div className="modal fade" id={dialogId} role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
					  <div className="modal-dialog" role="document">
					    <div className="modal-content">
					      <div className="modal-header">
					        <h5 className="modal-title" id="exampleModalLongTitle">Weather Forcast Details</h5>
					        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
					          <span aria-hidden="true">&times;</span>
					        </button>
					      </div>
					      <div className="modal-body">
							
								<ForcastDList forcastsD={this.props.forcastsD}/>
						  
					      </div>
					      
					    </div>
					  </div>
					</div>
        		</div> 
				
			
		)
	}
}
// end::detail[]

class ForcastDList extends React.Component {

	constructor(props) {
		super(props);
		
	}

	
	render() {
		var forcastList = this.props.forcastsD.map(forcastD =>
						<ForcastD
						forcastD={forcastD}
						forcastsD={this.props.forcastsD}/>
		);
		

		return (
			<div className="row justify-content-xs-center">
				{forcastList}
			</div>
			
			
		)
	}
	
}

class ForcastD extends React.Component {

	constructor(props) {
		super(props);
	}
	
	render() {
		
		var weatherMainList = this.props.forcastD.weather.map(weather =>
		<WeatherDetailMain 
		weather={weather}
		weathers={this.props.forcastD.weather}/>
		);
		
		var weatherDescList = this.props.forcastD.weather.map(weather =>
		<WeatherDetailDesc 
		weather={weather}
		weathers={this.props.forcastD.weather}/>
		);
					
		return (
							<table className="table">
						      	<tbody>
						        <tr>
						          <th>Date Time</th>
						          <th>{this.props.forcastD.dt_txt}</th>
						        </tr>
						        {weatherMainList}
						        {weatherDescList}
						        <tr>
						          <td>Temperature</td>
						          <td>{this.props.forcastD.main.temp}</td>
						        </tr>
						        <tr>
						          <td>Temperature Min</td>
						          <td>{this.props.forcastD.main.temp_min}</td>
						        </tr>
						        <tr>
						          <td>Temperature Max</td>
						          <td>{this.props.forcastD.main.temp_max}</td>
						        </tr>
						        <tr>
						          <td>Humidity</td>
						          <td>{this.props.forcastD.main.humidity}</td>
						        </tr>
						        <tr>
						          <td>Wind Speed</td>
						          <td>{this.props.forcastD.wind.speed}</td>
						        </tr>
						        
						      </tbody>
					      	</table>
		)
	}
}


//tag::weatherdetailmain[]
class WeatherDetailMain extends React.Component {

	constructor(props) {
		super(props);
	}

	
	render() {
		
		return (
				<tr>
			        <td>Weather Main</td>
			        <td>{this.props.weather.main}</td>
		        </tr>
			
		)
	}
}
// end::weatherdetailmain[]

//tag::weatherdetaildesc[]
class WeatherDetailDesc extends React.Component {

	constructor(props) {
		super(props);
	}

	
	render() {
		
		return (
				<tr>
			        <td>Weather Description</td>
			        <td>{this.props.weather.description}</td>
		        </tr>
			
		)
	}
}
// end::weatherdetaildesc[]


ReactDOM.render(
	<App />,
	document.getElementById('react')
)
