[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![HitCount](http://hits.dwyl.io/Niweera/wordhunter-v2.svg)](http://hits.dwyl.io/Niweera/wordhunter-v2)

# WordHunter-V2

## WordHunter-V2 is an ongoing project

## ([WordHunter-V1](https://github.com/Niweera/wordhunter) is live on https://wordhunter.niweera.gq)

> WordHunter lets you to find the words for the letters you have when you are playing Scrabble.

The following is the basic architecture of the WordHunter application. (The web application will use the WordHunter-API to get the results according to the letters that the user has given.)

![](https://i.imgur.com/jp5mcq2.jpg)

### Example use case

User wants to find words which can be created from `z, o, o`

The WordHunter service will provide the following results.

1. zoo - An establishment which maintains a collection of wild animals, typically in a park or gardens, for study, conservation, or display to the public.
2. oo - A honeyeater (bird) found in Hawaii, now probably extinct, which had a thin curved bill and climbed about on tree trunks.

(The initial version only gave the words which contains all the letters. This version provides all the letter combinations.)

## Meta

Nipuna Weerasekara – [@Niweera](https://twitter.com/Niweera) – w.nipuna@gmail.com

Distributed under the MIT license. See `LICENSE` for more information.

[https://github.com/Niweera/wordhunter-v2](https://github.com/Niweera/wordhunter-v2)

WordHunter-V2 is a combination of several microservices written in Java Spring Boot.
All services are cached with Redis server, load balanced with Ribbon Client and short-circuited with Netflix Hystrix. Inter-service communication is done via FeignClient.

1. [GoogleDict](https://googledict.herokuapp.com/) - (Dictionary API Service)
2. [Enygma](https://enygma.herokuapp.com/) - (Enygma Service for getting anagrams for a given letter combinations)
3. [WordHound](https://wordhound.herokuapp.com/) - (WordHound Service uses WordCache DB for providing definitions to a given word. It uses GoogleDict Service to populate the WordCache DB [A MongoDB instance])
4. [WordHunter-API](https://wordhunter-api.herokuapp.com/) - (The main entry-point to the WordHunter application, basically provides the words, and their definitions for a given letter combination)
5. Eureka-Server - (Eureka Discovery Server for service discovery)
6. [WordHunter-UI](https://wordhunter.niweera.gq/) - (UI for the WordHunter application)

## Contributing

1. Fork it (<https://github.com/Niweera/wordhunter-v2/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

<!-- Markdown link & img dfn's -->

[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[wiki]: https://github.com/yourname/yourproject/wiki

## Acknowledgement

Thanks to [Ammoniya](https://github.com/Ammoniya) for pointing me in the right direction.
