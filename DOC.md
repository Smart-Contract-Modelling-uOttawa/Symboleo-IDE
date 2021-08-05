# Symboleo
A Symboleo contract consists of domain types, contract parameters, variable declrations, conditions, obligations, powers, and constraints.  

# Types
- String
- Number
- Boolean
- Date
- Enum

# Ontology Types
- Event
- Asset
- Role

An example of domain model definitions:  
```
Domain Sample
Seller isA Role with address: String, name: String, capacity: Number, birthdate: Date, flag: Boolean;
Quality isAn Enumeration(PRIME, AAA, AA, A);
Good isAn Asset with quality: Quality, name: String;
SmartPhone isA Good with model: String; // Extending base types
Paid isAn Event with Env amount: Number, date: Date;
endDomain
```  
The "Env" attribute modifier on a property of the Event type makes that property fillable by the event sender.  

# Contract
A contract has a name and a list of parameters, example:  
```
Contract Sale (buyer : Buyer, seller : Seller, quantity : Number, deliveryDueDate: Date)
```


# Variable Declarations
We can decalare variables of the defined domain models in our contract. In the right side of assignments we can use arithmetic and boolean experssions. You can access nested properties using the dot syntax. You can also use a few functions for string and time manipulation, and mathematical operations.
```
Declarations 
product: SmartPhone with model := param1, quality := param2, name := String.lowercase(param3);
paidLate: PaidLate with amount := (1 + buyer.cell / Math.abs(100)), currency := curr, from := buyer, to := seller;
buyer: BuyerExt with result := (amt == 50 and qnt > 10 or (true and qnt > buyer.seller.capacity));
```
## Utility Funcitons
- String
- Math

# Proposition Syntax

## Predicate Functions
- Happens( Event ) 
- HappensBefore( Event, Point ) 
- HappensAfter( Event, Point ) 
- HappensWithin( Event, Interval ) 
- Occurs( Situation, Interval ) 

## Other Functions
- IsEqual( ID, ID ) 
- IsOwner( ID, ID ) 
- CannotBeAssigned( ID )


# Conditions
# Obligations
# Powers
# Survivig Obligations
# Powers
# Constraints


