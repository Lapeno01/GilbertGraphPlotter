package graph


case class Config(degree: Int = 5,            // constant degree
                  t: Int = 100,               // number of sample node pairs
                  maxN: Int = 500,            // maximum number of nodes
                  stepN: Int = 50             // step size for increasing nodes
                 )
