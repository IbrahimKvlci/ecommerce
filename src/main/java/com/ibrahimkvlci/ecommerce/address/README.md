# Address Module

The Address module provides comprehensive address management functionality for storing and managing geographical data with a hierarchical structure: Country → City → District → Neighborhood → AddressDetail.

## Features

- **Hierarchical Location Management**: Complete geographical hierarchy from country to neighborhood
- **Address Detail Management**: Store detailed address information with person details
- **Search & Filtering**: Advanced search capabilities across all levels
- **REST API**: Complete REST endpoints for all operations
- **Data Validation**: Comprehensive validation for all fields
- **Database Storage**: Optimized for storing city and country data

## Module Structure

```
address/
├── models/
│   ├── Country.java              # Country entity
│   ├── City.java                 # City entity (belongs to Country)
│   ├── District.java             # District entity (belongs to City)
│   ├── Neighborhood.java         # Neighborhood entity (belongs to District)
│   ├── AddressDetail.java        # Address detail entity (belongs to all above)
│   └── package-info.java
├── dto/
│   ├── CountryDTO.java           # Country data transfer object
│   ├── CityDTO.java              # City data transfer object
│   ├── DistrictDTO.java          # District data transfer object
│   ├── NeighborhoodDTO.java      # Neighborhood data transfer object
│   ├── AddressDetailDTO.java     # Address detail data transfer object
│   └── package-info.java
├── repositories/
│   ├── CountryRepository.java    # Country data access
│   ├── CityRepository.java       # City data access
│   ├── DistrictRepository.java   # District data access
│   ├── NeighborhoodRepository.java # Neighborhood data access
│   ├── AddressDetailRepository.java # Address detail data access
│   └── package-info.java
├── services/
│   ├── CountryService.java       # Country service interface
│   ├── CountryServiceImpl.java   # Country service implementation
│   ├── CityService.java          # City service interface
│   ├── CityServiceImpl.java      # City service implementation
│   ├── AddressDetailService.java # Address detail service interface
│   ├── AddressDetailServiceImpl.java # Address detail service implementation
│   └── package-info.java
├── controllers/
│   ├── CountryController.java    # Country REST controller
│   ├── CityController.java       # City REST controller
│   ├── AddressDetailController.java # Address detail REST controller
│   └── package-info.java
├── exceptions/
│   ├── AddressNotFoundException.java
│   ├── LocationNotFoundException.java
│   ├── AddressValidationException.java
│   └── package-info.java
├── application/
│   ├── AddressApp.java           # Application service
│   └── package-info.java
└── README.md
```

## Data Model

### Hierarchical Structure
```
Country (1) → (N) City (1) → (N) District (1) → (N) Neighborhood (1) → (N) AddressDetail
```

### Entity Details

#### Country
- `id`: Primary key
- `name`: Country name (unique)
- `code`: Country code (unique, max 3 characters)

#### City
- `id`: Primary key
- `name`: City name (unique)
- `country`: Reference to Country entity

#### District
- `id`: Primary key
- `name`: District name (unique)
- `city`: Reference to City entity

#### Neighborhood
- `id`: Primary key
- `name`: Neighborhood name (unique)
- `district`: Reference to District entity

#### AddressDetail
- `id`: Primary key
- `country`: Reference to Country entity
- `city`: Reference to City entity
- `district`: Reference to District entity
- `neighborhood`: Reference to Neighborhood entity
- `address`: Street address
- `name`: Person's first name
- `surname`: Person's last name
- `phone`: Phone number

## API Endpoints

### Country Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/address/countries` | Create new country |
| GET | `/api/address/countries` | Get all countries |
| GET | `/api/address/countries/{id}` | Get country by ID |
| GET | `/api/address/countries/name/{name}` | Get country by name |
| GET | `/api/address/countries/code/{code}` | Get country by code |
| GET | `/api/address/countries/search?name={name}` | Search countries by name |
| PUT | `/api/address/countries/{id}` | Update country |
| DELETE | `/api/address/countries/{id}` | Delete country |

### City Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/address/cities` | Create new city |
| GET | `/api/address/cities` | Get all cities |
| GET | `/api/address/cities/{id}` | Get city by ID |
| GET | `/api/address/cities/country/{countryId}` | Get cities by country |
| GET | `/api/address/cities/search?name={name}` | Search cities by name |
| GET | `/api/address/cities/search/country?countryId={id}&name={name}` | Search cities by country and name |
| PUT | `/api/address/cities/{id}` | Update city |
| DELETE | `/api/address/cities/{id}` | Delete city |

### Address Detail Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/address/address-details` | Create new address detail |
| GET | `/api/address/address-details` | Get all address details |
| GET | `/api/address/address-details/{id}` | Get address detail by ID |
| GET | `/api/address/address-details/country/{countryId}` | Get by country |
| GET | `/api/address/address-details/city/{cityId}` | Get by city |
| GET | `/api/address/address-details/district/{districtId}` | Get by district |
| GET | `/api/address/address-details/neighborhood/{neighborhoodId}` | Get by neighborhood |
| GET | `/api/address/address-details/search/name?name={name}` | Search by name |
| GET | `/api/address/address-details/search/surname?surname={surname}` | Search by surname |
| GET | `/api/address/address-details/search/phone?phone={phone}` | Search by phone |
| GET | `/api/address/address-details/search/address?address={address}` | Search by address |
| GET | `/api/address/address-details/search/name-surname?name={name}&surname={surname}` | Search by name and surname |
| GET | `/api/address/address-details/search/location?countryId={id}&cityId={id}` | Search by country and city |
| GET | `/api/address/address-details/search/location-district?countryId={id}&cityId={id}&districtId={id}` | Search by country, city and district |
| PUT | `/api/address/address-details/{id}` | Update address detail |
| DELETE | `/api/address/address-details/{id}` | Delete address detail |

## Usage Examples

### Create Country
```java
CountryDTO countryDTO = new CountryDTO();
countryDTO.setName("Turkey");
countryDTO.setCode("TR");

CountryDTO createdCountry = addressApp.createCountry(countryDTO);
```

### Create City
```java
CityDTO cityDTO = new CityDTO();
cityDTO.setName("Istanbul");
cityDTO.setCountryId(1L);

CityDTO createdCity = addressApp.createCity(cityDTO);
```

### Create Address Detail
```java
AddressDetailDTO addressDTO = new AddressDetailDTO();
addressDTO.setCountryId(1L);
addressDTO.setCityId(1L);
addressDTO.setDistrictId(1L);
addressDTO.setNeighborhoodId(1L);
addressDTO.setAddress("123 Main Street, Apt 4B");
addressDTO.setName("John");
addressDTO.setSurname("Doe");
addressDTO.setPhone("+90 555 123 4567");

AddressDetailDTO createdAddress = addressApp.createAddressDetail(addressDTO);
```

### Search Operations
```java
// Search by country
List<AddressDetailDTO> addresses = addressApp.getAddressDetailsByCountryId(1L);

// Search by city
List<AddressDetailDTO> addresses = addressApp.getAddressDetailsByCityId(1L);

// Search by name
List<AddressDetailDTO> addresses = addressApp.searchAddressDetailsByName("John");

// Search by phone
List<AddressDetailDTO> addresses = addressApp.searchAddressDetailsByPhone("555");
```

## Database Schema

The module creates the following tables:
- `countries`: Store country information
- `cities`: Store city information with country reference
- `districts`: Store district information with city reference
- `neighborhoods`: Store neighborhood information with district reference
- `address_details`: Store detailed address information with all location references

## Validation Rules

- **Country**: Name and code are required and unique
- **City**: Name is required and unique, country reference is required
- **District**: Name is required and unique, city reference is required
- **Neighborhood**: Name is required and unique, district reference is required
- **AddressDetail**: All location references, address, name, surname, and phone are required

## Error Handling

The module provides comprehensive error handling:
- **AddressNotFoundException**: When address detail is not found
- **LocationNotFoundException**: When location (country, city, etc.) is not found
- **AddressValidationException**: When validation fails

## Dependencies

- Spring Boot
- Spring Data JPA
- Jakarta Validation
- Lombok
- Spring Web

## Configuration

No additional configuration is required. The module uses standard Spring Boot auto-configuration for JPA and validation.
