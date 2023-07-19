resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
  instance_tenancy = "default"

  tags = {
    Name = "beerstore"
  }
}

resource "aws_subnet" "private_subnet" {
  count = 3
  vpc_id = aws_vpc.main.id
  cidr_block = cidrsubnet(aws_vpc.main.cidr_block, 8, count.index+10)

  availability_zone = var.availability_zones[count.index]

  tags = {
    Name = "beerstore_private_subnet_${count.index}"
  }
}

resource "aws_subnet" "public_subnet" {
  count = 3
  vpc_id = aws_vpc.main.id
  cidr_block = cidrsubnet(aws_vpc.main.cidr_block, 8, count.index + 20)

  availability_zone = var.availability_zones[count.index]

  map_public_ip_on_launch = true

  tags = {
    Name = "beerstore_public_subnet_${count.index}"
  }
}

resource "aws_route_table" "route_table" {
  vpc_id = aws_vpc.main.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.gw.id
  }

  tags = {
    Name = "beers_route_table"
  }
}

resource "aws_route_table_association" "association" {
  count = 3
  route_table_id = aws_route_table.route_table.id
  subnet_id = element(aws_subnet.public_subnet.*.id, count.index)
}
