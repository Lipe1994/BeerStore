terraform {
  backend "s3" {
    bucket = "proj-beerstore"
    key    = "beerstore.terraform"
    region = "us-east-1"
  }
}
